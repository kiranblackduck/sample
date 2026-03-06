#!/usr/bin/env php
<?php

class Greeter {
    private $name;

    public function __construct($name) {
        $this->name = $name;
    }

    public function greet() {
        echo "Hello, {$this->name} from PHP!\n";
    }
}

function calculateSum($numbers) {
    return array_sum($numbers);
}

function main() {
    $greeter = new Greeter('World');
    $greeter->greet();

    // Array operations
    $numbers = [1, 2, 3, 4, 5];
    $squares = array_map(fn($n) => $n ** 2, $numbers);
    $evenNumbers = array_filter($numbers, fn($n) => $n % 2 === 0);

    echo "Sum: " . calculateSum($numbers) . "\n";
    echo "Squares: " . implode(', ', $squares) . "\n";
    echo "Even numbers: " . implode(', ', $evenNumbers) . "\n";

    // Associative array
    $data = [
        'name' => 'PHP',
        'version' => '8.2',
        'type' => 'interpreted'
    ];

    foreach ($data as $key => $value) {
        echo "$key: $value\n";
    }
}

main();
?>
