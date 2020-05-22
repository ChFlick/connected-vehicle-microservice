
import React, { useState } from 'react';
import { Dayjs } from 'dayjs';
import { TimePicker } from '@material-ui/pickers';
import { Button } from '@material-ui/core';

type Props = {
  initStartTime: Dayjs,
  initEndTime: Dayjs,
  setTime: (start: Dayjs, end: Dayjs) => void;
}

const INTERVAL_SECONDS = 20;

const TimeOverlay: React.FC<Props> = ({ initStartTime, initEndTime, setTime }) => {
  const [startDate, handleStartDateChange] = useState(initStartTime);
  const [endDate, handleEndDateChange] = useState(initEndTime);
  const [isRealTime, setRealTime] = useState(false);

  let realTimeHandle: NodeJS.Timeout;
  const startRT = () => {
    let counter = 0;
    setRealTime(true);
    realTimeHandle = setInterval(() => {
      counter++;
      handleStartDateChange(startDate.add(counter * INTERVAL_SECONDS, "s"));
      handleEndDateChange(endDate.add(counter * INTERVAL_SECONDS, "s"));
      setTime(startDate.add(counter * INTERVAL_SECONDS, "s"), endDate.add(counter * INTERVAL_SECONDS, "s"));
    }, INTERVAL_SECONDS * 1000);
  }
  const endRT = () => {
    setRealTime(false);
    clearInterval(realTimeHandle);
  }

  return (
    <>
      <TimePicker ampm={false} label="Start" value={startDate} onChange={(date) => handleStartDateChange(date!)} />
      <TimePicker ampm={false} label="Ende" value={endDate} onChange={(date) => handleEndDateChange(date!)} />
      <Button variant="contained" color="primary" onClick={() => setTime(startDate, endDate)}>Set Time</Button>
      <Button variant="contained" color="primary" disabled={isRealTime} onClick={startRT}>Start real-time</Button>
      <Button variant="contained" color="primary" disabled={!isRealTime} onClick={endRT}>End real-time</Button>
    </>
  )
};

export default TimeOverlay;
