## Lexer generated notes

### Maybe opiska

{Bytearray, byteArray, bytearray, Byte_Array, Byte_array, byte_Array, byte_array} -> ByteArray
{string, STRING} -> String  
{uri, URI, url, Url, URL} -> Uri  

### Maybe different

{integer, Integer, INTEGER} -> Int
{Boolean, boolean, BOOLEAN} -> Bool
{set, SET} -> Set

### Not exists but have another

{byte, Byte, BYTE} -> {Int, ByteArray}  
{short, Short, SHORT} -> {Int}  
{long, Long, LONG} -> {Int}  
{double, Double, DOUBLE} -> {Int}  
{float, Float, Float} -> {Int}  
{decimal, Decimal, DECIMAL} -> ???   
{BigInt, BigInteger, ???} -> ???
{BigDecimal, ???} -> ???

{map, Map, MAP} -> {Set, ?tuple, ?array, ?map}  
{list, List, LIST} -> ?  
HashMap
TreeMap
Thread
