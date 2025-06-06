import { useState, type ChangeEvent, type FormEvent } from "react";
import './vehicle-register.css'
import { Box, Button, FormControl, FormControlLabel, FormLabel, InputLabel, MenuItem, Radio, RadioGroup, Select, TextField, type SelectChangeEvent } from "@mui/material";
import type { VehicleRegisterDto } from "../../../persistence/VehicleRegisterDto";
import type { PriorityDto } from "../../../persistence/PriorityDto";

  const defaultFormData: VehicleRegisterDto = {
    longitude: 0,
    latitude: 0,
    currentTimestamp: new Date(),
    isOccupied: false,
    driverId: 0,
    distanceSinceLastUpdate: 0,
    timeSinceLastUpdate: 0,
    priority: "LOW",
    emergencyDescription: ""
  }

// We are passing an annoymous function!
export default function VehicleRegister({onSuccess, priorityOptions} :  { onSuccess: () => void, priorityOptions: PriorityDto[] }) {
  const [formData, setFormData] = useState<VehicleRegisterDto>(defaultFormData);

  // Used for regular inputs
  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]:
        name === "priority" ? value :
        name === "isOccupied" ? value === "true" :
        name === "priority" ? value :
        name === "emergencyDescription" ? value :
        parseInt(value),
    }));
  };

  const handleSelectChange = (e: SelectChangeEvent<string>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    try {
      formData.currentTimestamp = new Date();
      const response = await fetch("http://localhost:8080/api/vehicles", {
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
        label="Longitude"
        name="longitude"
        value={formData.longitude}
        onChange={handleChange}
        size="small"
      />
      <TextField
        label="Latitude"
        name="latitude"
        value={formData.latitude}
        onChange={handleChange}
        size="small"
      />
      <FormControl component="fieldset" sx={{ alignItems: "center" }}>
        <FormLabel component="legend" sx={{ textAlign: "center" }}>
          Is Occupied?
        </FormLabel>
        <Box display="flex" justifyContent="center">
          <RadioGroup
            row
            name="isOccupied"
            value={String(formData.isOccupied)}
            onChange={handleChange}
          >
            <FormControlLabel value="true" control={<Radio />} label="Yes" />
            <FormControlLabel value="false" control={<Radio />} label="No" />
          </RadioGroup>
        </Box>
      </FormControl>
      <TextField
        label="Driver Id"
        name="driverId"
        value={formData.driverId}
        onChange={handleChange}
        size="small"
      />
      <TextField
        label="Distance Since Last Update"
        name="distanceSinceLastUpdate"
        value={formData.distanceSinceLastUpdate}
        onChange={handleChange}
        size="small"
      />
      <TextField
        label="Time Since Last Update"
        name="timeSinceLastUpdateiverId"
        value={formData.timeSinceLastUpdate}
        onChange={handleChange}
        size="small"
      />
      <FormControl fullWidth size="small">
        <InputLabel id="priority-label">Priority</InputLabel>
        <Select
          labelId="priority-label"
          id="priority"
          name="priority"
          value={formData.priority}
          label="Priority"
          onChange={handleSelectChange}
        >
          {priorityOptions.map((option) => (
            <MenuItem key={option.priority} value={option.priority}>
              {option.priority}
            </MenuItem>
          ))}
        </Select>
      </FormControl>
      <TextField
        label="Emergency Discription"
        name="emergencyDescription"
        value={formData.emergencyDescription}
        onChange={handleChange}
        size="small"
      />
      <Button type="submit" variant="contained" size="small">
        Submit
      </Button>
    </Box>

  )
}
