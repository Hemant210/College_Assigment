const mongoose = require('mongoose');

const GuestSchema = new mongoose.Schema({
    name: String,
    email: {
        type: String,
        unique: true
    },

    password: String
});

module.exports = mongoose.model('Guest', GuestSchema);