# MQTT to IR Bridge for Air Conditioner Control in Home Assistant

## 📋 Description

This project enables air conditioner control in Home Assistant using an infrared (IR) remote added to a Zigbee2MQTT setup.  
The application publishes discovery messages to Home Assistant, subscribes to MQTT topics for air conditioner commands, and posts corresponding IR commands to a Zigbee2MQTT topic. A Zigbee IR remote is responsible for sending the actual IR signals to the air conditioner.

## 🧩 Flow Diagram
The system flow is represented below, showing how the data moves between components:
```
    [Home Assistant]
(Sends "Turn on AC" message)
           ↓
      [MQTT Broker]
           ↓
         [App]
 (Converts message and
  publishes IR commands)
           ↓
      [MQTT Broker]
           ↓
      [Zigbee2MQTT]
(Sends IR signals via Zigbee)
           ↓
    [IR-Controlled AC]
```
## 🔧 Building and Run the Project

To build and run the project, follow these steps:

### Locally

1. `./gradlew clean build`
2. fill config/application.yml
3. `./gradlew bootRun`

### Docker
1. `docker build .`
2. `docker run -d \
  -e MQTT_URL=tcp://192.168.100.115:1883 \
  -e DEVICE_ID=0xa5c13813743bd4a2 \
  -e DEVICE_NAME=Medea \
  -e OUTPUT_TOPIC='zigbee2mqtt/Universal smart IR remote control/set/ir_code_to_send' \
  tabakov/ac-control:latest
`
    #### or
    `docker compose -f assets/compose.yml up -d`
