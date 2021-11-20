import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class EmergencyList extends Component {

  constructor(props) {
    super(props);
    this.state = {emergencies: [], isLoading: true};
  }

  componentDidMount() {
    this.setState({isLoading: true});

    fetch('/api/emergency-monitorings')
      .then(response => response.json())
      .then(data => this.setState({emergencies: data, isLoading: false}));
  }

  render() {
    const {emergencies, isLoading} = this.state;
	const {format} = require('date-fns');

    if (isLoading) {
      return <p>Загрузка...</p>;
    }

    const emergenciesList = emergencies.map(emergency => {
      const waterArea = `${emergency.waterArea.name}`;
	  const material = `${emergency.material.name} (${emergency.material.density})`;
      const emergencyType = `${emergency.emergencyType.name}`;
	  const startDate = `${format(new Date(emergency.createDate), 'dd.MM.yyyy HH:mm')}`;
	  const finishDate = `${format(new Date(emergency.finishDate), 'dd.MM.yyyy HH:mm')}`;

      return <tr key={emergency.id}>
        <td style={{whiteSpace: 'nowrap'}}>{startDate}</td>
        <td style={{whiteSpace: 'nowrap'}}>{finishDate}</td>
        <td>{waterArea}</td>
        <td>{emergencyType}</td>
        <td>{material}</td>
        <td style={{whiteSpace: 'nowrap'}}>{emergency.initialCoordinates}</td>
      </tr>
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
          </div>
          <h3>Исторические данные</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th>Дата начала</th>
              <th>Дата окончания</th>
              <th>Локация</th>
              <th>Тип ЧС</th>
              <th>Вещество</th>
              <th>Начальная площадь загрязнения</th>
            </tr>
            </thead>
            <tbody>
            {emergenciesList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default EmergencyList;