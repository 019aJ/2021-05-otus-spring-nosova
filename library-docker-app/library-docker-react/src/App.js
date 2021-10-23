import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import BookList from './BookList';
import BookEdit from './BookEdit';
import AuthorEdit from './AuthorEdit';
import GenreEdit from './GenreEdit';

class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={Home}/>
          <Route path='/books' exact={true} component={BookList}/>
          <Route path='/books/:id' component={BookEdit}/>
          <Route path='/authors' component={AuthorEdit}/>
          <Route path='/genres' component={GenreEdit}/>

        </Switch>
      </Router>
    )
  }
}

export default App;