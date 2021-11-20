import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import EmergencyList from './EmergencyList';
import MonitoringPage from './MonitoringPage';
import CreateMonitoring from './CreateMonitoring';


class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={Home}/>
          <Route path='/emergencies' exact={true} component={EmergencyList}/>
          <Route path='/monitoring' exact={true} component={MonitoringPage}/>
          <Route path='/create-monitoring' exact={true} component={CreateMonitoring}/>

        </Switch>
      </Router>
    )
  }
}

export default App;