import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import Select from 'react-select';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

class CreateMonitoring extends Component {

  emptyItem = {
	initialCoordinates: null,
    waterArea: {},
    emergencyType: {},
    material: {},
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem,
      waterAreas: [],
      emergencyTypes: [],
      materials: [],
	  isLoading: true
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleWaterAreaChange = this.handleWaterAreaChange.bind(this);
    this.handleMaterialChange = this.handleMaterialChange.bind(this);
    this.handleEmergencyTypeChange = this.handleEmergencyTypeChange.bind(this);

    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async componentDidMount() {
      const monitoring = this.emptyItem;
      const waterAreas = await (await fetch(`/api/waterAreas`)).json();
      const emergencyTypes = await (await fetch(`/api/emergencyTypes`)).json();
	  const materials = await (await fetch(`/api/materials`)).json();
      monitoring.waterArea = waterAreas[0];
      monitoring.emergencyType = emergencyTypes[0];
      monitoring.material = materials[0];
      this.setState({item: monitoring, waterAreas: waterAreas, emergencyTypes: emergencyTypes, materials: materials, isLoading: false});
	  
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item, waterAreas: this.state.waterAreas, emergencyTypes: this.state.emergencyTypes, materials: this.state.materials });
  }
  
  handleWaterAreaChange(event) {
    let item = this.state.item;
    item.waterArea = event.object;
    this.setState({item: item, waterAreas: this.state.waterAreas, emergencyTypes: this.state.emergencyTypes, materials: this.state.materials });
  }
  
  handleMaterialChange(event) {
    let item = this.state.item;
    item.material = event.object;
    this.setState({item: item, waterAreas: this.state.waterAreas, emergencyTypes: this.state.emergencyTypes, materials: this.state.materials });
  }
  
  handleEmergencyTypeChange(event) {
    let item = this.state.item;
    item.emergencyType = event.object;
    this.setState({item: item, waterAreas: this.state.waterAreas, emergencyTypes: this.state.emergencyTypes, materials: this.state.materials });
  }
  
  isAvailable(item){
      return item.status !== 403;
  }
  
  renderWaterAreasList() {
    return (this.state.waterAreas.map(waterArea =>({label: waterArea.name , value:waterArea.id, object: waterArea})));
  }
  
  renderMaterialsList() {
    return (this.state.materials.map(material =>({label: material.name, value:material.id, object: material})));
  }
  
  renderEmergencyTypesList() {
    return (this.state.emergencyTypes.map(emergencyType =>({label: emergencyType.name, value:emergencyType.id, object: emergencyType})));
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;

    await fetch('/api/emergency-monitorings', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    }).then(result => {if(result.status === 403){window.alert('emergency monitoring modification is not permited')}});
    this.props.history.push('/monitoring');
  }

  render() {
    if (this.state.isLoading) {
      return <p>Загрузка...</p>;
    }
    const {item,waterAreas,emergencyTypes,materials } = this.state;
    return <div>
      <AppNavbar/>
      <Container>
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="emergencyType">Тип ЧС</Label>
            <Select  name="emergencyType" id="emergencyType" 
                     value={item.emergencyType != null ? 
                              {label: item.emergencyType.name, value: item.emergencyType.id, object :item.emergencyType} :
                            emergencyTypes.length > 0 ? 
                              {label: emergencyTypes[0].name, value: emergencyTypes[0].id, object :emergencyTypes[0]} :
                              null}
                     onChange={this.handleEmergencyTypeChange}
                     options={this.renderEmergencyTypesList()} />
          </FormGroup>
          <FormGroup>
            <Label for="waterArea">Локация</Label>
            <Select name="waterArea" id="waterArea" 
                   value={item.waterArea != null ? 
                              {label:  item.waterArea.name, value: item.waterArea.id, object: item.waterArea} : 
                          waterAreas.length > 0 ? 
                              {label:  waterAreas[0].name, value: waterAreas[0].id, object: waterAreas[0]} :
                              null}
                   onChange={this.handleWaterAreaChange} options={this.renderWaterAreasList()} />
          </FormGroup>
          <FormGroup>
            <Label for="material">Вещество</Label>
            <Select  name="material" id="material" 
                     value={item.material != null ? 
                              {label: item.material.name, value: item.material.id, object :item.material} :
                            materials.length > 0 ? 
                              {label: materials[0].name, value: materials[0].id, object :materials[0]} :
                              null}
                     onChange={this.handleMaterialChange}
                     options={this.renderMaterialsList()} />
          </FormGroup>
          <FormGroup>
            <Label for="initialCoordinates">Начальная площадь загрязнения</Label>
            <Input type="text" name="initialCoordinates" id="initialCoordinates" value={item.initialCoordinates || ''}
                   onChange={this.handleChange} required />
          </FormGroup>
          <FormGroup>
            <Button color="primary" type="submit">Save</Button>{' '}
            <Button color="secondary" tag={Link} to="/monitoring">Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(CreateMonitoring);