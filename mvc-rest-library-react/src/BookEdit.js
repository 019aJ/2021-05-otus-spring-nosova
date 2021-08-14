import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import Select from 'react-select';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

class BookEdit extends Component {

  emptyItem = {
    title: '',
    author: null,
    genre: null
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem,
	  authors: [],
	  genres: []
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleAuthorChange = this.handleAuthorChange.bind(this);
    this.handleGenreChange = this.handleGenreChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async componentDidMount() {
      const book = this.props.match.params.id !== 'new' ? await (await fetch(`/api/books/${this.props.match.params.id}`)).json() : this.emptyItem;
      const authors = await (await fetch(`/api/authors`)).json();
      const genres = await (await fetch(`/api/genres`)).json();

      this.setState({item: book, authors: authors, genres: genres});
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
  }
  
  handleAuthorChange(event) {
    const value = event.value;
    let item = this.state.item;
    item['author'] = event.object;
    this.setState({item: item, authors: this.state.authors, genres: this.state.genres});
  }
  
  handleGenreChange(event) {
    const value = event.value;
    let item = this.state.item;
    item['genre'] = event.object;
    this.setState({item: item, authors: this.state.authors, genres: this.state.genres});
  }
  
  renderAuthorList() {
    return (this.state.authors.map(author =>({label: author.name + ' ' + author.surname, value:author.id, object: author})));
  }
  
  renderGenreList() {
    return (this.state.genres.map(genre =>({label: genre.name, value:genre.id, object: genre})));
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;

    await fetch('/api/books', {
      method: (item.id) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    });
    this.props.history.push('/books');
  }

  render() {
    const {item} = this.state;
    const title = <h2>{item.id ? 'Edit Book' : 'Add Book'}</h2>;

    return <div>
      <AppNavbar/>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="title">Title</Label>
            <Input type="text" name="title" id="title" value={item.title || ''}
                   onChange={this.handleChange} required />
          </FormGroup>
          <FormGroup>
            <Label for="author">Author</Label>
            <Select name="author" id="author" 
                   value={item.author != null ? 
                              {label:  item.author.name + ' ' + item.author.surname, value: item.author.id, object: item.author} : 
                          this.state.authors.length > 0 ? 
                              {label:  this.state.authors[0].name + ' ' + this.state.authors[0].surname, value: this.state.authors[0].id, object: this.state.authors[0]} :
                              null}
                   onChange={this.handleAuthorChange} options={this.renderAuthorList()} />
          </FormGroup>
          <FormGroup>
            <Label for="genre">Genre</Label>
            <Select  name="genre" id="genre" 
                     value={item.genre != null ? 
                              {label: item.genre.name, value: item.genre.id, object :item.genre} :
                            this.state.genres.length > 0 ? 
                              {label: this.state.genres[0].name, value: this.state.genres[0].id, object :this.state.genres[0]} :
                              null}
                     onChange={this.handleGenreChange}
                     options={this.renderGenreList()} />
          </FormGroup>
          <FormGroup>
            <Button color="primary" type="submit">Save</Button>{' '}
            <Button color="secondary" tag={Link} to="/books">Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default withRouter(BookEdit);