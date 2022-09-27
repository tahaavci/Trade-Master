import React, { Component } from "react";

import UserService from "../services/user.service";
import EventBus from "../common/EventBus";

export default class Dashboard extends Component {
  constructor(props) {
    super(props);
   
    this.state = {
      transactions: []
    };
  }

  componentDidMount() {
    UserService.getTransactionsBoard().then(
      response => {
        this.setState({
          transactions: response
        });
      },
      error => {
        this.setState({
          content:
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

  showDirection(inComingTransaction){

    if(!inComingTransaction)
      return "table-danger";
    else
      return "table-success";
    
  }

  listRows(){

    return this.state.transactions.map( (item) => {
      return(
              <tr key = {item.transactionId} className={ this.showDirection(item.transactionInComing)}>
                  <td className="text-center align-middle">{item.transactionId}</td>
                  <td className="text-center align-middle">{item.transactionAmount}</td>
                  <td className="text-center align-middle">{item.transactionDate}</td>
                  <td className="text-center align-middle">{item.transactionDesc}</td>
              </tr> 
      )
    });
   }


  render() {
    return (
      <div className="container">
        <header className="jumbotron">
          <h3 className="container mb-5">Transactions</h3>
              <table className="table">
                <thead>
                  <tr className="table-primary">
                    <th className="col text-center">Transaction Id</th>
                    <th className="col text-center">Amount</th>
                    <th className="col text-center">Date</th>
                    <th className="col text-center">Description</th>
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