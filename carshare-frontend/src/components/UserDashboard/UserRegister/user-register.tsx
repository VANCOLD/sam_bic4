import { useState, type ChangeEvent, type FormEvent } from "react";
import './user-register.css'
import { Box, Button, FormControl, FormControlLabel, FormLabel, Radio, RadioGroup, TextField } from "@mui/material";
import type { UserRegisterDto } from "../../../persistence/UserRegisterDto";

  const defaultFormData: UserRegisterDto = {
    username: '',
    firstname: '',
    surname: '',
    age: 0,
    fleetManager: false,
  }

// We are passing an annoymous function!
export default function UserRegister({onSuccess} :  { onSuccess: () => void }) {
  const [formData, setFormData] = useState<UserRegisterDto>(defaultFormData);

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]:
        name === "age" ? parseInt(value) :
        name === "fleetManager" ? value === "true" :
        value,
    }));
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    try {
      const response = await fetch("http://localhost:8080/api/users/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": "Bearer 1"
        },
        body: JSON.stringify(formData),
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const result = await response.json();
      console.log(result);
      onSuccess();
      setFormData(defaultFormData);
    } catch (error) {
      console.error("Failed to submit:", error);
    }
  };

  return (
    <Box
    component="form"
      onSubmit={handleSubmit}
      sx={{ display: "flex", flexDirection: "column", gap: 2}}
    >
      <TextField
        label="Username"
        name="username"
        value={formData.username}
        onChange={handleChange}
        size="small"
      />
      <TextField
        label="Firstname"
        name="firstname"
        value={formData.firstname}
        onChange={handleChange}
        size="small"
      />
      <TextField
        label="Surname"
        name="surname"
        value={formData.surname}
        onChange={handleChange}
        size="small"
      />
      <TextField
        label="Age"
        name="age"
        value={formData.age}
        onChange={handleChange}
        size="small"
      />
      <FormControl component="fieldset" sx={{ alignItems: "center" }}>
        <FormLabel component="legend" sx={{ textAlign: "center" }}>
          Fleet Manager
        </FormLabel>
        <Box display="flex" justifyContent="center">
          <RadioGroup
            row
            name="fleetManager"
            value={String(formData.fleetManager)}
            onChange={handleChange}
          >
            <FormControlLabel value="true" control={<Radio />} label="Yes" />
            <FormControlLabel value="false" control={<Radio />} label="No" />
          </RadioGroup>
        </Box>
      </FormControl>
      <Button type="submit" variant="contained" size="small">
        Submit
      </Button>
    </Box>

  )
}
