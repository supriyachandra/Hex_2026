const User= {
    id: 1,
    name: "MS Dhoni",
    skills: ["Batting", "Keeping", "Toss"]
}

// destructure 
const{id, name, skills}= User

// iterate over the list skills using ForEach arrow function
skills.forEach(s => console.log(`Skill: ${s}`))