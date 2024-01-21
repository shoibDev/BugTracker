import Axios from "axios";

Axios.defaults.baseURL = "http://localhost:8080";
Axios.defaults.headers.post["Content-Type"] = "application/json";

const API = {
  // Registration and login

  login: async function (userInfo) {
    try {
      const response = Axios.post(
        "/auth/authenticate",
        JSON.stringify(userInfo)
      );

      //Axios.defaults.headers.common["Authorization"] = `Bearer ${(await response).data.token}`; * FIX THIS
      return response;
    } catch (error) {
      console.log(error);
    }
  },

  // Dashboard and Project table

  getProjects: async function () {
    try {
      const response = await Axios.get("api/project", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });
      return response.data;
    } catch (error) {
      console.error("Error fetching projects:", error);
      throw error;
    }
  },

  getProjectById: async function (id) {
    const response = await Axios.get(`api/project/${id}`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
      },
    });
    return response.data;
  },

  createProject: async function (projectData) {
    try {
      const response = await Axios.post("api/project", projectData, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });
      return response.data;
    } catch (error) {
      console.error("Error fetching projects:", error);
      throw error;
    }
  },

  // User API's

  getUsers: async function () {
    try {
      const response = await Axios.get("api/user/all", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });
      return response.data;
    } catch (error) {
      console.error("Error fetching projects:", error);
      throw error;
    }
  },

  getAvailableUsers: async function (projectId) {
   return Axios.get(`api/project/${projectId}/availableDevs`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
    },
   })
  },

  addDevToProject: async function (projectId, userId) {
    try {
      const response = await Axios.post(
        `api/project/${projectId}/users/${userId}`,
        { userId }, // Assuming you want to send the userId in the request body
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
          },
        }
      );
      return response.data; // Axios wraps the response data in a `data` field
    } catch (error) {
      console.error("Error adding developer to project:", error);
      throw error;
    }
  },

  updateProject: async function (projectId, projectData) {
    try {
      const response = await Axios.put(`api/project/${projectId}/edit`, projectData, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
        },
      });
      return response.data;
    } catch (error) {
      console.error("Error updating project:", error);
      throw error;
    }
  },

  deleteProject: function (projectId) {
    return Axios.delete(`api/project/${projectId}/delete`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("jwtToken")}`,
      },
    })
  },
};

export default API;
