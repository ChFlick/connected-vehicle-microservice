/* global google */

import dayjs from 'dayjs';
import GoogleMapReact, { Coords } from 'google-map-react';
import React from 'react';
import './App.css';
import { Configuration, DefaultApi, Vehicle } from './service';
import VehicleList from './VehicleList';
import { API_KEY } from './mapsapikey';

type Position = {
  lat: Number;
  lng: Number;
  weight?: Number;
}
interface HeatmapProp {
  positions: Position[];
  options: {
    radius?: number;
    opacity?: number;
  };
}

type State = {
  vehicles: Vehicle[]
}

class App extends React.Component<{}, State> {
  googleMap: GoogleMapReact | null = null;
  center: Coords = { lng: 7.4, lat: 43.7372312 };
  zoom = 14;

  api = new DefaultApi(new Configuration({ basePath: 'http://localhost:8083' }));

  heatmapProp: HeatmapProp = {
    positions: [],
    options: {
      opacity: 1,
      radius: 10,
    }
  };

  state = {
    vehicles: [],
  };

  componentDidMount = () => {
    setTimeout(() => this.api.trafficVehiclesBusesBetweenGet({
      start: dayjs().subtract(5, "minute").toDate(),
      end: dayjs().toDate(),
    }).then((vehicles) => {
      console.log("Number of busses:", vehicles.length);
      this.setState(() => ({ vehicles }));

      const positions = vehicles.map(vehicle => ({
        // @ts-ignore
        location: new google.maps.LatLng(vehicle.longitude!, vehicle.latitude!),
        weight: 1 + (vehicle.personNumber || 0),
      }));

      // this.heatmapProp.positions = positions;
      positions.forEach(p => {
        if (this.googleMap) {
          (this.googleMap as any).heatmap.data.push(p);
        }
      });
    }), 1000)
  }

  render = () => (
    <div className="app">
      <div className="map-container" id='mapContainer'>
        <GoogleMapReact
          bootstrapURLKeys={{
            key: API_KEY,
            libraries: ['visualization']
          }}
          defaultCenter={this.center}
          defaultZoom={this.zoom}
          heatmapLibrary={true}
          // @ts-ignore
          heatmap={this.heatmapProp}
          ref={(el) => this.googleMap = el}
        />
      </div>

      <div>
        <VehicleList vehicles={this.state.vehicles} />
      </div>
    </div>
  );
}

export default App;
