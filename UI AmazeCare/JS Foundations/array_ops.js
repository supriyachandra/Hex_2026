const stocks = [
    {symbol: 'NTPC' , price: 360, change: 2.5},
    {symbol: 'SBI' , price: 756, change: -0.5},
    {symbol: 'HDFC' , price: 970, change: -1.2},
    {symbol: 'APL APOLLO' , price: 240, change: 1.6}
]

// 1. Filter this array to display stocks that returned positive for the day 
const positive_stocks= stocks.filter(stock=> stock.change>0);

//console.log(positive_stocks);
positive_stocks.map(stock=>{ 
    console.log(`${stock.symbol} - ${stock.change}`)
})

// 2. Filter array to show only negative stocks --- we are sending new array that has filtered data
const negative_stocks= stocks.filter(stock=> stock.change<0);

negative_stocks.map(stock=>{ 
    console.log(`${stock.symbol} - ${stock.change}`)
})
console.log("-----------------------")
// 3. Sort in ascending order of their price
//sorting requires a cloned list

const asc_stocks= [...stocks].sort((s1,s2) => s1.price -s2.price)
console.log(asc_stocks);

console.log("-----------------------")

const desc_stocks= [...stocks].sort((s1,s2) => s2.price -s1.price)
console.log(desc_stocks);

// find as per symbol (if exists then symbol, if invalid then inform it)
const sname= 'SBI'
const stockObj= stocks.find(stock=> stock.symbol == sname);
console.log(stockObj== undefined? "Stock not found": stockObj);