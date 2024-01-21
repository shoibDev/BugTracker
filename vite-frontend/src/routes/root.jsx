import { Outlet, Link, Navigate, useNavigate } from "react-router-dom";
import { useEffect } from "react";
import SideBarData from "../assets/sideBarData";
import "./root.css";

import BugReportRoundedIcon from "@mui/icons-material/BugReportRounded";

const Root = () => {
  return (
    <>
      <nav className="sidebar">
        <div className="navbar-header">
          <h1>Bug Tracker</h1>
          <span> <BugReportRoundedIcon style={{ fontSize: 30 }} /> </span>
        </div>
        <hr />
        <ul>
          {SideBarData.map((item, index) => (
            <li className="nav-item" key={index}>
              <Link to={item.path} className="nav-link">
                <div className="nav-icon">{item.icon}</div>
                <span>{item.title}</span>
              </Link>
            </li>
          ))}
        </ul>
      </nav>

      <div className="main-content">
        <Outlet />
      </div>
    </>
  );
};

export default Root;
