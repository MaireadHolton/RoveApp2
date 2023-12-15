const express = require('express');
const bodyParser = require ('body-parser');
const cors = require('cors');

const app = express();

app.use(bodyParser.json());
app.use(cors());

const PORT = process.env.PORT || 3000;

app.get('/visits/', (req,res) => {
var data = req.body.data
});
//app.get('/visits/{email}', visits.findAll);
//app.get('/visits/{email}/{id}', visits.findOne);

app.post('/visits/{email}', (req, res) => {
var data = req.body.data
});

//app.put('/visits/{email}/{id}', visits.editVisit);

//app.delete('/visits/{email}/{id}', visits.deleteVisit);

app.listen(PORT, () => {
    console.log('Server is running on port {$PORT}');
});




