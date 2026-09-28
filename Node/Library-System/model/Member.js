const mongoose = require('mongoose');

const MemberSchema = new mongoose.Schema({
    name: String,
    email: {
        type: String,
        Unique: true
    },

    password: String
});

module.exports = mongoose.model('Member', MemberSchema);