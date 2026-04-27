const User= {
    id: 1,
    name: "MS Dhoni",
    role: "Captain"
}

// print old way
console.log(User.name)

// for new way do -destructuring ---react
const{id,name,role}= User

console.log(`The user name is ${name}`)