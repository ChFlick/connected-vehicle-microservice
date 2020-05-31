
import React, { useState } from 'react';
import { Dayjs } from 'dayjs';
import { TimePicker } from '@material-ui/pickers';
import { Button, TextField } from '@material-ui/core';

type Props = {
  initStartTime: Dayjs,
  initEndTime: Dayjs,
  setTime: (start: Dayjs, end: Dayjs) => void;
}


const TimeOverlay: React.FC<Props> = ({ initStartTime, initEndTime, setTime }) => {
  const [startDate, handleStartDateChange] = useState(initStartTime);
  const [endDate, handleEndDateChange] = useState(initEndTime);
  const [isRealTime, setRealTime] = useState(false);
  const [realTimeHandle, setRealTimeHandle] = useState<NodeJS.Timeout>();
  const [refreshTime, setRefreshTime] = useState(20);

  const startRT = () => {
    let counter = 0;
    setRealTime(true);
    setRealTimeHandle(setInterval(() => {
      counter++;
      handleStartDateChange(startDate.add(counter * refreshTime, "s"));
      handleEndDateChange(endDate.add(counter * refreshTime, "s"));
      setTime(startDate.add(counter * refreshTime, "s"), endDate.add(counter * refreshTime, "s"));
    }, refreshTime * 1000));
  }
  const endRT = () => {
    setRealTime(false);
    clearInterval(realTimeHandle!);
  }

  return (
    <>
      <TimePicker ampm={false} label="Start" value={startDate} onChange={(date) => handleStartDateChange(date!)} />
      <TimePicker ampm={false} label="Ende" value={endDate} onChange={(date) => handleEndDateChange(date!)} />
      <Button variant="contained" color="primary" onClick={() => setTime(startDate, endDate)}>Set Time</Button>
      <Button variant="contained" color="primary" disabled={isRealTime} onClick={startRT}>Start real-time</Button>
      <Button variant="contained" color="primary" disabled={!isRealTime} onClick={endRT}>End real-time</Button>
      <TextField color="primary" disabled={isRealTime} type="number" label="Refresh time in seconds"
        InputLabelProps={{
          shrink: true,
        }}
        value={refreshTime}
        onChange={(time) => setRefreshTime(parseInt(time.target.value))}
      />
    </>
  )
};

export default TimeOverlay;
