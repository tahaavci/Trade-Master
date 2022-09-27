import React, { Component } from "react";
import {Link} from "react-router-dom";

import UserService from "../services/user.service";
import EventBus from "../common/EventBus";

export default class Dashboard extends Component {
  constructor(props) {
    super(props);

    this.listRows = this.listRows.bind(this);
    this.saveAccount=this.saveAccount.bind(this);

    this.state = {
      accountsInfo:[]
    };
  }

  componentDidMount() {

    UserService.getDashboard().then(
      response => {

        this.setState({
          accountsInfo: response
        });
      },
      error => {
        this.setState({
          accountsInfo:
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString()
        });

        if (error.response && error.response.status === 401) {
          EventBus.dispatch("logout");
        }
        if(!this.accountsInfo)
          window.location.href = "/home";
      
      }
    );
  }
   saveAccount(id){
    localStorage.removeItem("accountId");
    localStorage.setItem("accountId", id);
   }

   listRows(){

    return this.state.accountsInfo.map( (item) => {

      return(
              <tr key = {item.accountId}>
                  <td className="text-center align-middle">{item.accountId}</td>
                  <td className="text-center align-middle">{item.accountBalance}</td>
                  <td className="text-center align-middle">{item.accountType}</td>
              
                  <td className="text-center align-middle" >
                      <Link onClick={ ()=>this.saveAccount(item.accountId)}
                         className="btn btn-primary"
                         to={"/transaction" }
                         >Transactions
                      </Link>
                  </td>
              </tr> 
      )
    });
   }



  render() {

    return (
      <div className="container">
        <header className="jumbotron">
          <h3 className="container mb-5">Account Info</h3>
              <table className="table">
                <thead>
                  <tr>
                    <th className="col text-center">Account Id</th>
                    <th className="col text-center">Balance</th>
                    <th className="col text-center">Type</th>
                    <th className="col text-center">Details</th>
                  </tr>
                </thead>
                <tbody>
 
                  {this.listRows()}
                 
                </tbody>
              </table>

        </header>
      </div>
    );
  }
}