
import React, { useState } from 'react';
import { Dayjs } from 'dayjs';
import { TimePicker } from '@material-ui/pickers';

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
      <TimePicker ampm={false} label="Start" value={startDate} onChange={(date) => {
        if (date) {
          handleStartDateChange(date);
          setTime(date, endDate);
        }
      }} />
      <TimePicker ampm={false} label="Ende" value={endDate} onChange={(date) => {
        if (date) {
          handleEndDateChange(date);
          setTime(startDate, date);
        }
      }} />
    </>
  )
};

export default TimeOverlay;
