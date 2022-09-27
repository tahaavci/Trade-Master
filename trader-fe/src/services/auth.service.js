import axios from "axios";

const API_URL = "http://localhost:8080/v1/";

class AuthService {
  login(email, password) {
    return axios
      .post(API_URL + "login", {
        email,
        password
      })
      .then(response => {
        if (response.data.token) {
          localStorage.setItem("user", JSON.stringify(response.data));
        }

        return response.data;
      });
  }

  logout() {
    localStorage.removeItem("user");
    localStorage.removeItem("accountId");
  }


  getCurrentUser() {
    return JSON.parse(localStorage.getItem('user'));;
  }
}

export default new AuthService();