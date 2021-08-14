import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class BookList extends Component {

  constructor(props) {
    super(props);
    this.state = {groups: [], isLoading: true};
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});

    fetch('api/books')
      .then(response => response.json())
      .then(data => this.setState({books: data, isLoading: false}));
  }

  async remove(id) {
    await fetch('/api/books/${id}', {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      }
    }).then(() => {
      let updatedBooks = [...this.state.books].filter(i => i.id !== id);
      this.setState({books: updatedBooks});
    });
  }

  render() {
    const {books, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    const bookList = books.map(book => {
      const author = `${book.author.name} ${book.author.surname}`;
	  const genre = `${book.genre.name}`;
      return <tr key={book.id}>
        <td style={{whiteSpace: 'nowrap'}}>{book.title}</td>
        <td>{author}</td>
        <td>{genre}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/books/" + book.id}>Edit</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(book.id)}>Delete</Button>
          </ButtonGroup>
        </td>
      </tr>
    });

    return (
      <div>
        <AppNavbar/>
        <Container fluid>
          <div className="float-right">
            <Button className = "bl-button" color="success" tag={Link} to="/books/new">Add</Button>
            <Button className = "bl-button" color="success" tag={Link} to="/authors/new">Add Author</Button>
            <Button className = "bl-button" color="success" tag={Link} to="/genres/new">Add Genre</Button>
          </div>
          <h3>Library</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th>Title</th>
              <th width="20%">Author</th>
              <th width="20%">Genre</th>
              <th width="10%">Actions</th>
            </tr>
            </thead>
            <tbody>
            {bookList}
            </tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default BookList;