import React from "react";
import { NotificationContainer, NotificationManager } from 'react-notifications';
import 'react-notifications/lib/notifications.css';

import UserService from "../services/user.service";
import EventBus from "../common/EventBus";


export default class Exchange extends React.Component {


  constructor(props) {
    super(props);

    this.state = {
      accountsInfo: [],
      disabled: false,
      loading: false,
      exchangeInfo: "",
      formVisible: "visible",
      approveVisible:"invisible d-none",
      currencies:[]
    };

    this.listAccountOption = this.listAccountOption.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleApprove = this.handleApprove.bind(this);
    this.listCurrency = this.listCurrency.bind(this);
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
        if (!this.accountsInfo) {
          window.location.href = "/home";
        }
      }
    );


    UserService.retrieveCurrency().then(
      response => {
        this.setState({
          currencies: response
        }); 
      },
      error => {
        console.log(error);
        this.setState({
          exchangeInfo:
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString()
        });

        NotificationManager.error(error.response);
      });


  }

  listAccountOption() {

    return this.state.accountsInfo.map((item) => {
      return (

        <option key={item.accountId}
          value={item.accountId} name={item.accountType}>
          {item.accountBalance + "-" + item.accountType}
        </option>
      );
    }
    );

  }

  handleSubmit = (event) => {
    event.preventDefault();

    this.setState({
      loading: true,
      disabled: true
    });

    if (!event.target.amountInput.value) {
      NotificationManager.warning('Amount Should Not Be Zero');
      this.setState({
        loading: false,
        disabled: false
      });
    } else if (!event.target.checkBox.checked) {
      NotificationManager.warning('Approve the Transaction');
      this.setState({
        loading: false,
        disabled: false
      });
    }
    else {


      var src = event.target.srcSelect.value;
      var dst = event.target.dstSelect.value;
      var amount = event.target.amountInput.value;

      UserService.startExchange(src, dst, amount).then(
        response => {
          this.setState({
            exchangeInfo: response,
            loading: false,
            disabled: false,
            formVisible: "invisible d-none",
            approveVisible:"visible"
          });

          localStorage.removeItem("accountId");
          localStorage.setItem("accountId", dst);

        },
        error => {
          event.preventDefault();
          console.log(error);
          this.setState({
            exchangeInfo:
              (error.response &&
                error.response.data &&
                error.response.data.message) ||
              error.message ||
              error.toString()
          });

          NotificationManager.error(error.response.data);

          this.setState({
            loading: false,
            disabled: false
          });


        }
      )


    }
  }


  handleApprove =(event)=>{

    event.preventDefault();
    

    this.setState({
      loading: true,
      disabled: true
    });

    var queueId = this.state.exchangeInfo.queueId;
    if(event.target.id==='reject'){

      window.location.reload();

    }else{

          UserService.approveExchange(queueId,true).then(
            response => {
              this.setState({
                approveInfo: response,
                loading: false
              });

              NotificationManager.success('Transaction Completed.', 'Success',3000);
              
            
            window.setTimeout(function(){
                window.location.href = "/dashboard";
            }, 3500);

            },
            error => {
              event.preventDefault();
              console.log(error);
              this.setState({
                exchangeInfo:
                  (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                  error.message ||
                  error.toString()
              });

              NotificationManager.error(error.response);

              this.setState({
                loading: false,
                disabled: false
              });


            }
          )
    }
}

  listCurrency(){

  
    return this.state.currencies.map( (item) => {

      return(
              <tr key = {item.currencyId}>
                  <td className="text-center align-middle">{item.srcCurrency+"-"+item.dstCurrency}</td>
                  <td className="text-center align-middle">{item.sell}</td>
                  <td className="text-center align-middle">{item.buy}</td>
                  <td className="text-center align-middle">{item.date}</td>
              </tr> 
      )
    });


  }


  render() {
    return (
      <div className="container">
        <header className="jumbotron">
          <h3 className="container mb-5">Exchange Platform</h3>
          <table className="table">
            <thead>
              <tr className="table-primary">

                <th className="col text-center">Currency</th>
                <th className="col text-center">Sell</th>
                <th className="col text-center">Buy</th>
                <th className="col text-center">Date</th>
              </tr>
            </thead>
            <tbody>

              {this.listCurrency()}

            </tbody>
          </table>
        </header>


        <div className="container mt-3">

          <div className="col-md-12">

            <div className={"card card-container "} >

              <form className={this.state.approveVisible}>
                <div className="mb-3">
                <label htmlFor="exampleInputEmail1" className="form-label center">Amount : {this.state.exchangeInfo.totalAmount}</label>
                      
                </div>
                <div className="mb-3">
                <label htmlFor="exampleInputPassword1" className="form-label">Currency : {this.state.exchangeInfo.exchangeRate}</label>
                 </div>
                   
                <div className="mb-3">
                      {this.state.loading && (<span className="spinner-border spinner-border-sm"></span>)}
                </div>

                 <div className="d-flex mt-3">
                     <button onClick={this.handleApprove} id={'reject'}type="submit" className="btn btn-primary flex-fill m-2" disabled={this.state.disabled}>Reject</button>

                     <button onClick={this.handleApprove} id={'accept'} type="submit" className="btn btn-primary flex-fill m-2" disabled={this.state.disabled}>Approve</button>

                  </div>
              </form> 

              <form onSubmit={this.handleSubmit} className={this.state.formVisible}  >

                <label htmlFor="floatingSelect">Src Account</label>
                <select disabled={this.state.disabled} className="form-select mb-4"
                  name="srcSelect"
                  aria-label="Floating label select example">
                  {this.listAccountOption()}

                </select>

                <label htmlFor="floatingSelect">Dst Account</label>
                <select disabled={this.state.disabled} className="form-select mb-4" name="dstSelect" id="floatingSelect" aria-label="Floating label select example">
                  {this.listAccountOption()}
                </select>
                <input disabled={this.state.disabled} name="amountInput" type="number" id="number" min="1"  step=".01" placeholder="Amount"></input>


                <div className="mb-3 mt-3 form-check">
                  <input type="checkbox" name="checkBox" className="form-check-input" id="exampleCheck1"></input>
                  <label className="form-check-label" htmlFor="exampleCheck1">Do you approve the transaction?</label>
                </div>

                <div className="form-group mt-4 mb-4">
                  <div className="d-grid gap-2">
                    <button
                      className="btn btn-primary btn-block"
                      disabled={this.state.loading}
                    >
                      {this.state.loading && (
                        <span className="spinner-border spinner-border-sm"></span>
                      )}
                      <span>Buy</span>
                    </button>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
        <NotificationContainer />
      </div>
    );
  }
}