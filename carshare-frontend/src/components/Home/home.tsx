import { Box, Typography } from "@mui/material";
import "./home.css"

export default function Home() {
  return (
    <Box>
      <Typography variant="h1" align="center" sx={{my:10}}>
        Welcome to the future of carsharing
      </Typography>
    </Box>
  );  
}