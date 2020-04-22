
import React, { useState } from 'react';
import './App.css';
import { Vehicle } from './service';

import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import { Switch, FormControlLabel } from '@material-ui/core';

type Props = {
  vehicles: Vehicle[];
}

type State = {
  showEmptyVehicles: boolean
}

const VehicleList: React.FC<Props> = ({ vehicles }) => {
  const [showEmptyVehicles, setShowEmptyVehicles] = useState(true);

  return (
    <>
      <FormControlLabel
        control={<Switch
          checked={showEmptyVehicles}
          onChange={(change) => setShowEmptyVehicles(change.target.checked)}
          name="showEmptyVehicles"
          inputProps={{ 'aria-label': 'Show empty vehicles' }}
        />}
        label="Show empty vehicles"
      />
      <TableContainer component={Paper}>
        <Table aria-label="vehicle means table" size="small">
          <TableHead>
            <TableRow>
              <TableCell>Vehicle</TableCell>
              <TableCell align="left">Time</TableCell>
              <TableCell align="right">Capacity</TableCell>
              <TableCell align="right">Speed</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {vehicles.filter((vehicle) => showEmptyVehicles || (vehicle.personNumber || 0) > 0).map((vehicle) => (
              <TableRow key={vehicle.vehicleId + "@" + vehicle.time}>
                <TableCell component="th" scope="row">
                  {vehicle.vehicleId}
                </TableCell>
                <TableCell align="left">{vehicle.time}</TableCell>
                <TableCell align="right">{vehicle.personNumber} / {vehicle.personCapacity}</TableCell>
                <TableCell align="right">{vehicle.speed}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </>
  );
};

export default VehicleList;
