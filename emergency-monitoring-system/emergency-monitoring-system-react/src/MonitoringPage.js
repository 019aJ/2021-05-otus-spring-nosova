import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import OLMapFragment from './OLMapFragment'

class MonitoringPage extends Component {

  constructor(props) {
    super(props);
    this.state = {monitorings: [], isLoading: true};
  }

  componentDidMount() {
    this.setState({isLoading: true});

    fetch('/api/active-emergency-monitorings')
      .then(response => response.json())
      .then(data => this.setState({monitorings: data, isLoading: false}));
  }

  async finish(monitoring) {
    await fetch('/api/emergency-monitorings/', 
      {
      method: 'PUT',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(monitoring)
    })
    .then((result) => {
        let updatedMonitorings = [...this.state.monitorings].filter(i => i !== monitoring);
        this.setState({monitorings: updatedMonitorings});
        }
    );
  }

  render() {
    const {monitorings, isLoading} = this.state;
	const {format} = require('date-fns');

    if (isLoading) {
      return <p>Загрузка...</p>;
    }

    const monitoringsList = monitorings.map(monitoring => {
      const waterArea = `${monitoring.waterArea.name}`;
	  const material = `${monitoring.material.name} (${monitoring.material.density})`;
      const emergencyType = `${monitoring.emergencyType.name}`;
      const emergencyDate = `${format(new Date(monitoring.createDate), 'dd.MM.yyyy HH:mm')}`;
      const contaminationArea = `${monitoring.contaminationArea}`;
	  const date = `${format(new Date(monitoring.createDate), 'dd.MM.yyyy HH:mm')}`;
      return <tr key={monitoring.id}>
        <td>{date}</td>
        <td>{emergencyType}</td>
        <td>{emergencyDate}</td>
        <td>{waterArea}</td>
        <td>{material}</td>
        <td>{monitoring.initialCoordinates}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" onClick={() => this.finish(monitoring)}>Завершить наблюдение</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
          </div>
          <h3>Мониторинг</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th>Начало мониторинга</th>
              <th>Тип ЧС</th>
              <th>Начало ЧС</th>
              <th>Локация</th>
              <th>Вещество</th>
              <th>Начальная площадь загрязнения</th>
            </tr>
            </thead>
            <tbody>
            {monitoringsList}
            </tbody>
          </Table>
		  <div className="mapdiv">
		    <OLMapFragment dataFromParent = {monitorings}/>
		  </div>
        </Container>
      </div>
    );
  }
}

export default MonitoringPage;