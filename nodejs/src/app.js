const http = require('http');
const { addUser, getUser, removeUser, getUsersInRoom } = require('./utils/users');
const { generateMessage } = require('./utils//messages');
const express = require('express');
const socketio = require('socket.io');
const Filter = require('bad-words');

const log = console.log;

// setup express server.
const app = express();
const server = http.createServer(app);

// connect socket io to the server created above.
const io = socketio(server);

io.on('connection', (socket) => {
    log('new websocket connection!');
    socket.on('join', (options, callback) => {
        const { error, user } = addUser({ id: socket.id, username: options.username, room: options.room });
        if (error) {
            console.log(error);
            return callback(false);
        }
        socket.join(user.room);
        socket.emit('message', generateMessage('Admin', `Welcome to chat room ${user.room}`));
        socket.broadcast.to(user.room).emit('message', generateMessage('Admin', `${user.username} has joined!`));
        io.to(user.room).emit('roomData', {
            room: user.room,
            users: getUsersInRoom(user.room)
        });
        callback(true);
    });

    socket.on('sendMessage', (message, callback) => {
        const user = getUser(socket.id);
        const filter = new Filter();
        if (filter.isProfane(message)) {
            return callback(false);
        }
        log(`A new Message From ${user.username} in the room ${user.room}, the message: ${message}`)
        socket.broadcast.to(user.room).emit('message', generateMessage(user.username, message));
        callback(true);
    });

    socket.on('disconnect', () => {
        const {user, error} = removeUser(socket.id);
        if(!error){
            if (user) {
                log(`${user.username} has left the room ${user.room}`);
                io.to(user.room).emit('message', generateMessage('Admin', `${user.username} has left!`));
                io.to(user.room).emit('roomData', {
                    room: user.room,
                    users: getUsersInRoom(user.room)
                });
            }
        }
    });

});

server.listen(3000, () => log('server is running on port 3000'));

// Acknowledgement
// server (emit) -> client ( receive ) -> --acknowledgement --> server
// client (emit) -> server ( receive ) -> --acknowledgement --> client
