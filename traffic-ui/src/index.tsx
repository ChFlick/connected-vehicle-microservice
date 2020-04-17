import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import { LocalizationProvider } from '@material-ui/pickers';
import DayjsAdapter from '@material-ui/pickers/adapter/dayjs'

const Root = () => (<LocalizationProvider dateAdapter={DayjsAdapter}><App /></LocalizationProvider>)

ReactDOM.render(<Root />, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
