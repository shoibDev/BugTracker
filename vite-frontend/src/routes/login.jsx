import { useState } from "react";
import "../routes/login.css";
import API from "../assets/API";
import {
    Form,
    FormGroup,
    Label,
    Input,
    Button,
  } from "reactstrap";

const Login = () => {
  const [loginInfo, setLoginInfo] = useState({ email: "", password: "" });

  const handleChange = (event) => {
    setLoginInfo((prev) => {
      return { ...prev, [event.target.name]: event.target.value };
    });
  };

  async function onSubmit(event){
    try{
        const response = await API.login(loginInfo);
        if(response.status >= 200 && response.status < 300){
            const jwtToken = response.data.token;
            const auth = response.data.role;

            localStorage.setItem("jwtToken", jwtToken);
            localStorage.setItem("auth", auth);

            console.log(localStorage.getItem("jwtToken"));
        }
    }catch(error){
        console.log(error);
    }
  }

  return (
    <div className="login-container">
      <div className="login-box">
        <h2>Login</h2>
        <Form>
          <div className="input-group">
            <Label>Email</Label>
            <Input
              name="email"
              type="email"
              value={loginInfo.email}
              onChange={handleChange}
            />
          </div>
          <div className="input-group">
            <Label>Password</Label>
            <Input
              name="password"
              type="password"
              value={loginInfo.password}
              onChange={handleChange}
            />
          </div>
          <Button className="login-btn" onClick={onSubmit}>
            Login
          </Button>
        </Form>
      </div>
    </div>
  );
};

export default Login;
