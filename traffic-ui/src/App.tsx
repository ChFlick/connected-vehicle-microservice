import React from 'react';
import { Map, View, Feature } from 'ol';
import { Heatmap, Tile } from 'ol/layer';
import { OSM, Vector } from 'ol/source';
import { fromLonLat } from 'ol/proj';
import './App.css';
import Point from 'ol/geom/Point';
import { DefaultApi, Configuration } from './service';


class App extends React.Component {
  center = [7.4309, 43.7349];
  zoom = 14;

  api = new DefaultApi(new Configuration({ basePath: 'http://localhost:8083' }));

  componentDidMount = () => {

    const point = new Feature({
      geometry: new Point(fromLonLat(this.center)),
      weight: 1,
    });
    const point1 = new Feature({
      geometry: new Point(fromLonLat(this.center)),
      weight: 1,
    });
    const point2 = new Feature({
      geometry: new Point(fromLonLat(this.center)),
      weight: 1,
    });

    const data = new Vector();
    data.addFeatures([point, point1, point2]);

    new Map({
      target: 'mapContainer',
      layers: [
        new Tile({
          source: new OSM(),
        }),
        new Heatmap({
          source: data,
        }),
      ],
      view: new View({
        center: fromLonLat(this.center),
        zoom: this.zoom,
      }),
    });
  }

  render = () => (
    <div className="app">
      <div className="map-container" id='mapContainer'></div>
    </div>
  );
}

export default App;
