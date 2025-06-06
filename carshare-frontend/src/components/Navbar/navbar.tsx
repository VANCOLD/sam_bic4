import { AppBar, Toolbar, Typography, Box, Button } from "@mui/material";
import { Link } from "react-router-dom";
import "./navbar.css"

export default function Navbar() {
return (
    <AppBar position="static">
      <Toolbar>
        <Typography variant="h6" sx={{ flexGrow: 1 }}>
          CarShare
        </Typography>

        <Box sx={{ display: "flex", gap: 2 }}>
          <Button color="inherit" component={Link} to="/">
            Home
          </Button>
          <Button color="inherit" component={Link} to="/user-dashboard">
            User Dashboard
          </Button>
          <Button color="inherit" component={Link} to="/vehicle-dashboard">
            Vehicle Dashboard
          </Button>
        </Box>
      </Toolbar>
    </AppBar>
  );
}