const Ticket = {
    id: 1,
    subject: 'Internet down',
    details: 'Some details',
    priority: 'HIGH',
    status: 'OPEN'
}

// in updation in react, we clone the object and update the cloned object 

const updatedTicket={
    ...Ticket,
    status: "CLOSED"
}

console.log(Ticket);
console.log(updatedTicket);