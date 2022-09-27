import React, { Component } from "react";
import { Route, Link, Routes } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AuthService from "./services/auth.service";

import Login from "./components/login.component";
import Home from "./components/home.component";
import Dashboard from "./components/dashboard.component";
import Transaction from "./components/transaction.component";
import Exchange from "./components/exchange";
import EventBus from "./common/EventBus";


class App extends Component {
  
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);

    
    this.state = {
      currentUser: undefined,
      showDashboard: false,
    };
  }

  componentDidMount() {
    const user = AuthService.getCurrentUser();

    if (user) {
      this.setState({
        currentUser: user,
        showDashboard: true
      });
    }
    
    EventBus.on("logout", () => {
      this.logOut();
    });
  }

  componentWillUnmount() {
    EventBus.remove("logout");
  }

  logOut() {
    AuthService.logout();
    this.setState({
      showDashboard: false,
      currentUser: undefined
    });
  }

  render() {

    const { currentUser, showDashboard} = this.state;

    return (
      <div>
        <nav className="navbar navbar-expand navbar-dark bg-dark">
          <Link to={"/home"} className="navbar-brand p-2">
            Trade Master
          </Link>
          <div className="navbar-nav me-auto">
            <li className="nav-item">
              <Link to={"/home"} className="nav-link">
                Home
              </Link>
            </li>
      
            {showDashboard && (
              <div className="navbar-nav ms-auto">
                <li className="nav-item">
                    <Link to={"/dashboard"} className="nav-link">
                      Dashboard
                    </Link>
                </li>
              </div>
            )}

            {currentUser && (
              <li className="nav-item">
                <Link to={"/exchange"} className="nav-link">
                  Exchange
                </Link>
              </li>
            )}
          </div>

          {currentUser ? (
            <div className="navbar-nav ms-auto">
              <li className="nav-item">
                <Link to={"/dashboard"} className="nav-link">
                  {currentUser.email}
                </Link>
              </li>
              <li className="nav-item">
                <a href="/login" className="nav-link" onClick={this.logOut}>
                  LogOut
                </a>
              </li>
            </div>
          ) : (
            <div className="navbar-nav ms-auto">
              <li className="nav-item">
                <Link to={"/login"} className="nav-link">
                  Login
                </Link>
              </li>
            </div>
          )}
        </nav>
        <div className="container mt-3">
          <Routes>
            <Route exact path="/" element={<Home/>} />
            <Route exact path="/home" element={<Home/>} />
            <Route exact path="/login/*" element={<Login/>} />
            <Route exact path="/dashboard" element={<Dashboard/>} />
            <Route path="/transaction" element={<Transaction/>} />
            <Route path="/exchange" element={<Exchange/>} />
          </Routes>
        </div>
      </div>
    );
  }
}
export default App;