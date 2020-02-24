import React from 'react';
import { Map, View, Feature } from 'ol';
import { Heatmap, Tile } from 'ol/layer';
import { OSM, Vector } from 'ol/source';
import { fromLonLat } from 'ol/proj';
import './App.css';
import Geometry from 'ol/geom/Geometry';
import FeatureFormat from 'ol/format/Feature';
import Point from 'ol/geom/Point';


class App extends React.Component {
  componentDidMount = () => {
    const center = [7.4309, 43.7349];
    const zoom = 14;

    const point = new Feature({
      geometry: new Point(fromLonLat(center)),
      weight: 1,
    });
    const point1 = new Feature({
      geometry: new Point(fromLonLat(center)),
      weight: 1,
    });
    const point2 = new Feature({
      geometry: new Point(fromLonLat(center)),
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
        center: fromLonLat(center),
        zoom,
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
