import React, { useState, useEffect } from "react";
import { ToastContainer, toast } from "react-toastify";
import {
  Button,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
  FormGroup,
  Label,
  Form,
  Input,
} from "reactstrap";
import API from "../assets/API";
import "react-toastify/dist/ReactToastify.css";

function CreateProject(props) {
  const projectList = props.projects;
  const [modal, setModal] = useState(false);

  const [request, setRequest] = useState({ name: "", description: "", team: []});
  const [availableTeamMembers, setAvailableTeamMembers] = useState([]);

  const toggle = () => setModal(!modal);

  const handleChange = (event) => {
    let value;

    if (
      event.target.type === "select" ||
      event.target.type === "select-multiple"
    ) {
      value = Array.from(
        event.target.selectedOptions,
        (option) => option.value
      );
    } else {
      value = event.target.value;
    }
    const name = event.target.name;

    setRequest({
      ...request,
      [name]: value,
    });
  };

  async function onSubmit(event) {
    event.preventDefault();
  
    try {
      // Check if a project with the same name already exists
      const projectName = request.name;
      const projectExists = projectList.some(project => project.name === projectName);
  
      if (projectExists) {
        // Display an alert or error message indicating duplicate project name
        toast.error("Project Already Exists!", {
          position: "top-right",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
        return;
      }
  
      let projectId = await API.createProject(request);
  
      request.team.forEach(async (userId) => {
        let user = userId;
        await API.addDevToProject(projectId.id, user);
      });
  
      // Clear the form inputs and close the modal
      setRequest({ name: "", description: "", team: [] });
      props.toggle();
      props.render(); // Call the toggleRender function
    } catch (err) {
      console.log(err);
    }
  }
  

  useEffect(() => {
    let isRendered = true;

    async function fetchUsers() {
      const users = await API.getUsers();
      console.log(users)
      if (isRendered === true) setAvailableTeamMembers(users);
    }

    fetchUsers();

    return () => {
      isRendered = false;
    };
  }, []);

  return (
    <div>
        <ModalBody>
          <Form>
            <FormGroup>
              <Label
                htmlFor="name"
                className="lease-form-label mandatory-entry"
              >
                Project Name
              </Label>
              <Input
                id="name"
                type="text"
                name="name"
                className="lease-form-input"
                placeholder="Enter project name"
                value={request.name}
                onChange={handleChange}
              />
            </FormGroup>
            <FormGroup>
              <Label for="description">Project Description</Label>
              <Input
                type="textarea"
                name="description"
                id="description"
                placeholder="Enter description"
                value={request.description}
                onChange={handleChange}
                rows="5"
              />
            </FormGroup>
            <FormGroup>
              <Label for="team">Add Team Members</Label>
              <Input
                type="select"
                name="team"
                id="team"
                value={request.team}
                onChange={handleChange}
                multiple
              >
                {availableTeamMembers.map((user, key) => {
                  return (
                    <option key={key} value={user.id}>
                      {user.first_name} {user.last_name}
                    </option>
                  );
                })}
              </Input>
            </FormGroup>
          </Form>
        </ModalBody>
        <ModalFooter>
          <Button color="primary" onClick={onSubmit}>
            Submit
          </Button>{" "}
          <Button color="secondary" onClick={toggle}>
            Cancel
          </Button>
        </ModalFooter>
        <ToastContainer />
    </div>
  );
}

export default CreateProject;
