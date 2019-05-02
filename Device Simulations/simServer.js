var express = require('express')
var bodyParser = require('body-parser')

//inits
const app = express()
const port = 3000

//uses
app.use(bodyParser.urlencoded({ extended: true }))
app.use(bodyParser.json())


//setup
app.listen(port,()=>
    console.log(`Communication Module: listening on port ${port}!`)
)
app.get("/", function(req,res){
    return res.send("What now bitch")
})
app.post("/", function(req,res){
    console.log(req.body)
    return res.send('Data received' + req.body)
})