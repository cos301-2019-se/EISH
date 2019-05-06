class device {
    deviceName;
    topic;
    minWatt;
    maxWatt;
    consumption;
    state;

    constructor (object){
        this.deviceName = object.name;
        this.topic = object.topic;
        this.minWatt = object.minWatt;
        this.maxWatt = object.maxWatt;
        this.consumption = object.consumption;
        this.state = object.state;
    }
    getName()
    {
        return this.deviceName;
    }
    setName(name)
    {
        this.deviceName = name
    }
    getTopic()
    {
        return this.topic;
    }

    setTopic(newTopic)
    {
        this.topic = newTopic;
    }

    getMinWatt()
    {
        return this.minWatt;
    }

    setMinWatt(watts)
    {
        this.minWatt = watts;
    }

    getMaxWatt()
    {
        return this.maxWatt;
    }

    setMaxWatt(watts)
    {
        this.maxWatt = watts;
    }

    getConsumption()
    {
        return this.consumption;
    }
    setConsumption(con)
    {
        this.consumption = con;
    }

    getState()
    {
        return this.state;
    }

    setState(stat)
    {
        this.state = stat;
    }

    
    configure(client){
        client.on('connect',()=>{
            client.subscribe("cmnd/"+this.topic+"/Power");
            client.subscribe('cmnd/"+this.topic+"/Status');
            setInterval(publishConsumption(client),timeInterval);
        })

        client.on('message',(topic, message) => 
        {
            if(topic == "cmnd/"+this.topic+"/Power")
            {
                var msg = message.toString()
                if(msg =="")
                {
                    client.publish("cmnd/"+this.topic+"/RESULT", {"POWER":deviceState?"ON":"OFF"})
                    client.publish("stat/"+this.topic+"/POWER" ,deviceState?"ON":"OFF")
                }
                else if(msg =="TOGGLE")
                {
                    deviceState = !deviceState
                    client.publish("cmnd/"+this.topic+"/RESULT", {"POWER":deviceState?"ON":"OFF"})
                    client.publish("stat/"+this.topic+"/POWER" ,deviceState?"ON":"OFF")
                }
            }

            if(topic == "cmnd/"+this.topic+"/Status"){
                var msg = message.toString()
                if(msg == "8")
                {
                    publishConsumption(client);
                }
            }

            
        })

    }

        publishConsumption(client)
        {
            var currentCon = getCurrentConsumption()
            client.publish("cmnd/"+this.topic+"/Status",{"Power":currentCon})

        }

        getCurrentConsumption()
        {
            if(this.state == true)
                currentConsumption = randomConsumption(this.minWatt,this.maxWatt);
            else{
                currentConsumption = 0;
            }

            return this.consumption
        }

        randomConsumption(min, max)
        {
            return Math.random()* (max-min)+min
        }
}
