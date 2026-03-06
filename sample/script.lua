-- Lua Script Sample for Language Detection

-- Variables
local greeting = "Hello, World from Lua!"
local numbers = {1, 2, 3, 4, 5}

-- Function to greet
local function greet(name)
    print("Hello, " .. name .. "!")
end

-- Function to calculate sum
local function calculateSum(nums)
    local sum = 0
    for _, num in ipairs(nums) do
        sum = sum + num
    end
    return sum
end

-- Function to map
local function map(tbl, func)
    local result = {}
    for i, v in ipairs(tbl) do
        result[i] = func(v)
    end
    return result
end

-- Function to filter
local function filter(tbl, predicate)
    local result = {}
    for _, v in ipairs(tbl) do
        if predicate(v) then
            table.insert(result, v)
        end
    end
    return result
end

-- Main script
print(greeting)
greet("User")

-- Array operations
print("Numbers: " .. table.concat(numbers, ", "))
print("Sum: " .. calculateSum(numbers))

-- Map example
local squares = map(numbers, function(x) return x * x end)
print("Squares: " .. table.concat(squares, ", "))

-- Filter example
local evenNumbers = filter(numbers, function(x) return x % 2 == 0 end)
print("Even numbers: " .. table.concat(evenNumbers, ", "))

-- Table (object) example
local person = {
    name = "Alice",
    age = 30,
    roles = {"admin", "user"}
}

print("\nPerson:")
print("  Name: " .. person.name)
print("  Age: " .. person.age)
print("  Roles: " .. table.concat(person.roles, ", "))

-- Metatables (Lua's version of OOP)
local Greeter = {}
Greeter.__index = Greeter

function Greeter.new(name)
    local self = setmetatable({}, Greeter)
    self.name = name
    return self
end

function Greeter:greet()
    print("Hello, " .. self.name .. " from Lua class!")
end

local greeter = Greeter.new("World")
greeter:greet()

-- Conditional example
for _, num in ipairs(numbers) do
    if num % 2 == 0 then
        print(num .. " is even")
    else
        print(num .. " is odd")
    end
end

-- Coroutines example
local co = coroutine.create(function()
    for i = 1, 3 do
        print("Coroutine iteration: " .. i)
        coroutine.yield()
    end
end)

while coroutine.status(co) ~= "dead" do
    coroutine.resume(co)
end
