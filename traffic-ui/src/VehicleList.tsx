
import React from 'react';
import './App.css';
import { Vehicle } from './service';

import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

type Props = {
  vehicles: Vehicle[];
}

const VehicleList: React.FC<Props> = ({ vehicles }) => (
  <TableContainer component={Paper}>
    <Table aria-label="simple table">
      <TableHead>
        <TableRow>
          <TableCell>Vehicle</TableCell>
          <TableCell align="right">Position</TableCell>
          <TableCell align="right">Capacity</TableCell>
          <TableCell align="right">Speed</TableCell>
        </TableRow>
      </TableHead>
      <TableBody>
        {vehicles.map((vehicle) => (
          <TableRow key={vehicle.vehicleId}>
            <TableCell component="th" scope="row">
              {vehicle.vehicleId}
            </TableCell>
            <TableCell align="right">Lat: {vehicle.latitude} Lng: {vehicle.longitude}</TableCell>
            <TableCell align="right">{vehicle.personNumber} / {vehicle.personCapacity}</TableCell>
            <TableCell align="right">{vehicle.speed}</TableCell>
          </TableRow>
        ))}
      </TableBody>
    </Table>
  </TableContainer>
);

export default VehicleList;
