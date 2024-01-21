import React, { useState, useEffect } from "react";
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Label, FormGroup, Input, Form, Container,  } from "reactstrap";
import API from "../assets/API";


function UpdateProject(props) {
  const [projectData, setProjectData] = useState({ name: "", description: "" })
  const [availableTeamMembers, setAvailableTeamMembers] = useState([]);
  const [team, setTeam] = useState([]);

	useEffect(() => {
		const fetchProject = async () => {
			const response = await API.getProjectById(props.projectId);
			setProjectData(response);
		};
	
		fetchProject();
	}, []);

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

    setProjectData({
      ...projectData,
      [name]: value,
    });
  };

  async function onSubmit(event) {
    event.preventDefault();

    await API.updateProject(props.projectId, projectData);

    setProjectData({ name: "", description: ""});

    props.resetProjectId();
    props.toggle();
  }

  return (
    <div>
      <Container fluid>
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
								value={projectData.name}
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
                value={projectData.description}
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
                value={team}
                onChange={handleChange}
                multiple
              >
                {availableTeamMembers.map((user, key) => {
                  return (
                    <option key={key} value={user.id}>
                      {user.firstName} {user.lastName}
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
          <Button color="secondary">
            Cancel
          </Button>
        </ModalFooter>
      </Container>
    </div>
  );
}

export default UpdateProject;
