import axios from 'axios';
//import { json } from 'react-router-dom';
import authHeader from './auth-header';

const ACC_API_URL = 'http://localhost:8080/v1/';
const EXC_API_URL = 'http://localhost:8090/v1/';

class UserService {

  getHomeContent() {
    return axios.get(ACC_API_URL + 'home');
  }

  async getDashboard() {

    const items = JSON.parse(localStorage.getItem('user'));

    const num = items.customerId;

    var postData = {
      id: num
    };

    const responseDashboard = await axios.post(ACC_API_URL + 'account/accounts', postData, { headers: authHeader() });
    return JSON.parse(JSON.stringify(responseDashboard.data));
  }


  async getTransactionsBoard() {

    var postData = {
      accountId: localStorage.getItem('accountId')
    };

    const responseTransaction = await axios.post(EXC_API_URL + 'transaction/account', postData,{ headers: authHeader() });


    return JSON.parse(JSON.stringify(responseTransaction.data));
  }

  async startExchange(srcAcc,dstAcc,trAmount){

    var postData = {
      srcAccount:srcAcc,
      dstAccount:dstAcc,
      amount:trAmount,
    };

    const responseTransaction = await axios.post(EXC_API_URL + 'exchange', postData,{ headers: authHeader() });
    console.log("startExchange : ");
    var a = JSON.parse(JSON.stringify(responseTransaction.data));
    console.log(a);
    console.log("startexchange bitti");
    return JSON.parse(JSON.stringify(responseTransaction.data));
  }


  async approveExchange(queue,approve){

    var postData = {
      queueId:queue,
      isApproved:approve
    };

    const responseApprove = await axios.post(EXC_API_URL + 'approve-transaction', postData,{ headers: authHeader() });

    return JSON.parse(JSON.stringify(responseApprove.data));
  }

  async retrieveCurrency(){


    const responseApprove = await axios.get(EXC_API_URL + 'currency');
    

    return JSON.parse(JSON.stringify(responseApprove.data));
  }

}

export default new UserService();