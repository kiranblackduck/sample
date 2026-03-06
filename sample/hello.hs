module Main where

-- Data type definition
data Greeter = Greeter { name :: String }

-- Function to greet
greet :: Greeter -> IO ()
greet (Greeter name) = putStrLn $ "Hello, " ++ name ++ " from Haskell!"

-- Calculate sum using fold
calculateSum :: [Int] -> Int
calculateSum = foldr (+) 0

-- Calculate squares using map
squares :: [Int] -> [Int]
squares = map (^2)

-- Filter even numbers
evenNumbers :: [Int] -> [Int]
evenNumbers = filter even

main :: IO ()
main = do
    let greeter = Greeter "World"
    greet greeter

    let numbers = [1, 2, 3, 4, 5]
    putStrLn $ "Sum: " ++ show (calculateSum numbers)
    putStrLn $ "Squares: " ++ show (squares numbers)
    putStrLn $ "Even numbers: " ++ show (evenNumbers numbers)

    -- List comprehension
    let squaresList = [x^2 | x <- numbers]
    putStrLn $ "Squares (comprehension): " ++ show squaresList
