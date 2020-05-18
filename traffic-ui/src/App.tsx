/* global google */

import dayjs, { Dayjs } from 'dayjs';
import GoogleMapReact, { Coords } from 'google-map-react';
import React from 'react';
import './App.css';
import { API_KEY } from './mapsapikey';
import { Configuration, DefaultApi, Vehicle } from './service';
import TimeOverlay from './TimeOverlay';
import VehicleList from './VehicleList';

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
  vehicleMeans: Vehicle[]
}

const INIT_START_TIME = dayjs("2020-01-01T07:00:00.000Z");
const INIT_END_TIME = dayjs("2020-01-01T07:10:00.000Z");

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
    vehicleMeans: [],
  };

  componentDidMount() {
    setTimeout(() => this.setTime(INIT_START_TIME, INIT_END_TIME), 1000);
  }

  setTime = (start: Dayjs, end: Dayjs) => {
    this.api.publicTransportBusesMeanDataBetweenGet({
      start: start.toDate(),
      end: end.toDate()
    }).then((vehicleMeans) => {
      this.setState(() => ({ vehicleMeans }));
    })

    this.api.publicTransportBusesBetweenGet({
      start: start.toDate(),
      end: end.toDate(),
    }).then((vehicles) => {
      console.log("Number of busses:", vehicles.length);
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
    });
  }

  render = () => (
    <div className="app">
      <TimeOverlay
        initStartTime={INIT_START_TIME}
        initEndTime={INIT_END_TIME}
        setTime={this.setTime}
      />
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
        <VehicleList vehicles={this.state.vehicleMeans} />
      </div>
    </div>
  );
}

export default App;
