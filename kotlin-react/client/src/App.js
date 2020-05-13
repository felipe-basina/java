import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import { Security, SecureRoute, ImplicitCallback } from '@okta/okta-react';
import CoffeeShopsList from './CoffeeShopsList';
import CoffeeShopEdit from './CoffeeShopEdit';
import { withAuth } from '@okta/okta-react';
import Api from './Api';
import NavBar from './NavBar';

const AuthWrapper = withAuth(class WrappedRoutes extends Component {

  constructor(props) {
    super(props);
    this.state = { authenticated: null, user: null, api: new Api() };
    this.checkAuthentication = this.checkAuthentication.bind(this);
  }

  async checkAuthentication() {
    const authenticated = await this.props.auth.isAuthenticated();
    if (authenticated !== this.state.authenticated) {
      if (authenticated) {
        const user = await this.props.auth.getUser();
        let accessToken = await this.props.auth.getAccessToken();
        this.setState({
          authenticated: authenticated,
          user: user,
          api: new Api(accessToken),
        });
      } else {
        this.setState({
          authenticated: authenticated,
          user: null,
          api: new Api(),          
        });
      }
    }
  }

  async componentDidMount() {
    this.checkAuthentication();
  }

  async componentDidUpdate() {
    this.checkAuthentication();
  }

  async login() {
    if (this.state.authenticated === null) {
      return;
    }
    this.props.auth.login("/");
  }

  async logout() {
    this.props.auth.logout("/");
  }

  render() {
    let { authenticated, user, api } = this.state;
    if (authenticated === null) {
      return null;
    }

    const navbar = <NavBar isAuthenticated={authenticated} 
      login={this.login.bind(this)}
      logout={this.logout.bind(this)} />;

    return (
      <Switch>
        <Route
          path='/'
          exact={true}
          render={(props) => <Home {...props} authenticated={authenticated} user={user} api={api} navbar={navbar}/>}
        />
        <SecureRoute
          path='/coffee-shops'
          exact={true}
          render={(props) => <CoffeeShopsList {...props} authenticated={authenticated} user={user} api={api} navbar={navbar}/>}
        />
        <SecureRoute
          path='/coffee-shops/:id'
          exact={true}
          render={(props) => <CoffeeShopEdit {...props} authenticated={authenticated} user={user} api={api} navbar={navbar}/>}
        />
      </Switch>
    );
  }

});

class App extends Component {
  
  render() {
      return (
        <Router>
          <Security issuer='https://dev-590272.okta.com/oauth2/default'
            clientId='0oabrngt0PlrcFWUV4x6'
            redirectUri={window.location.origin + '/implicit/callback'}
            pkce={true}>
              <Route path='/implicit/callback' component={ImplicitCallback} />
              <AuthWrapper />
          </Security>
        </Router>
      );
  }

}

export default App;
