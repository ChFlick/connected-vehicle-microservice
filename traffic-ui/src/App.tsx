/* global google */

import React from 'react';
import GoogleMapReact, { Coords } from 'google-map-react';
import './App.css';
import { DefaultApi, Configuration } from './service';

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

class App extends React.Component {
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

  componentDidMount = () => {
    setTimeout(() => this.api.trafficVehiclesBusesSinceLastFiveMinutesGet().then((vehicles) => {
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
            key: 'AIzaSyDcCVy39I9x2nIHxi0FoB2MImUgTFmoEA4',
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
    </div>
  );
}

export default App;
