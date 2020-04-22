
import React, { useState } from 'react';
import { Dayjs } from 'dayjs';
import { TimePicker } from '@material-ui/pickers';
import { Button } from '@material-ui/core';

type Props = {
  initStartTime: Dayjs,
  initEndTime: Dayjs,
  setTime: (start: Dayjs, end: Dayjs) => void;
}

const TimeOverlay: React.FC<Props> = ({ initStartTime, initEndTime, setTime }) => {
  const [startDate, handleStartDateChange] = useState(initStartTime);
  const [endDate, handleEndDateChange] = useState(initEndTime);

  return (
    <>
      <TimePicker ampm={false} label="Start" value={startDate} onChange={(date) => handleStartDateChange(date!)} />
      <TimePicker ampm={false} label="Ende" value={endDate} onChange={(date) => handleEndDateChange(date!)} />
      <Button variant="contained" color="primary" onClick={() => setTime(startDate, endDate)}>Set Time</Button>
    </>
  )
};

export default TimeOverlay;
