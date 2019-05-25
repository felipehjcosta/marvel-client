const main = require('./build/webpack/main');

console.log("Calling Kotlin from Javascript")
console.log(main.sample.hello())
const newInstance = main.com.github.felipehjcosta.marvelclient.ApplicationApi.Companion.newInstance
const applicationApi = newInstance(process.env.MARVEL_PRIVATE_KEY, process.env.MARVEL_PUBLIC_KEY)
console.log("fetchCharacters")
applicationApi.fetchCharacters(function (result) {
    console.log(result)
})