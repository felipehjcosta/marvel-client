const main = require('../build/js/packages/marvel-client-library/kotlin/marvel-client-library');

console.log("Calling Kotlin from Javascript")
console.log(main.sample.hello())
const marvelclient = main.com.github.felipehjcosta.marvelclient
const applicationApi = marvelclient.createApplicationApi(process.env.MARVEL_PRIVATE_KEY, process.env.MARVEL_PUBLIC_KEY)
console.log("fetchCharacters")
applicationApi.fetchCharacters(function (result) {
    console.log(result)
})