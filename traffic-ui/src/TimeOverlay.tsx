
import React, { useState } from 'react';
import dayjs, { Dayjs } from 'dayjs';
import { TimePicker } from '@material-ui/pickers';

type Props = {
  setTime: (start: Dayjs, end: Dayjs) => void;
}

const TimeOverlay: React.FC<Props> = ({ setTime }) => {
  const [startDate, handleStartDateChange] = useState(dayjs());
  const [endDate, handleEndDateChange] = useState(dayjs());

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
