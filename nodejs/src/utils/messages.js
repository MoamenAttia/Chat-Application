const generateMessage = (username, text, createdAt) => {
    return {
        text,
        username,
        createdAt
    };
};

module.exports = { generateMessage };