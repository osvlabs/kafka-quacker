/*
 * kafka-quacker
 *
 *
 * Contact: zhangwb@shinetechchina.com
 */

package main

import (
	"fmt"
	"kafka-quacker/app"
	"os"
	"runtime"
)

func main() {
	fmt.Printf("Server started\n")

	quackerConfig := app.QuackerConfig{

		Host:               getEnv("QUACKER_HOST", "127.0.0.1"), // "kafka.osvie.com",
		Port:               getEnv("QUACKER_PORT", "9091"),
		SecurityProtocol:   getEnv("QUACKER_SECURITY_PROTOCOL", "PLAINTEXT"), // PLAINTEXT or SSL. Not support SASL_PLAINTEXT or SASL_SSL yet
		TrustStore:         getEnv("QUACKER_TRUSTSTORE", ""),                 // truststore file path.
		TrustStorePassword: getEnv("QUACKER_TRUSTSTORE_PASSWORD", ""),        // truststore file password.
		KeyStore:           getEnv("QUACKER_KEYSTORE", ""),                   // keystore file path.
		KeyStorePassword:   getEnv("QUACKER_KEYSTORE_PASSWORD", ""),          // keystore file password.
		KeyPassword:        getEnv("QUACKER_KEY_PASSWORD", ""),               // key password.
		Topic:              getEnvOrFail("QUACKER_TOPIC"),                    // Your kafka topic name.
		ClientId:           getEnv("QUACKER_CLIENTID", "kafka-quacker"),
		Interval:           getEnv("QUACKER_INTERVAL", "1"),
		DataFile:           getEnv("QUACKER_DATAFILE", "/data.json"),
		DryRun:             getEnv("QUACKER_DRYRUN", "") != "",
	}

	quacker := app.NewQuacker(quackerConfig)
	runtime.SetFinalizer(&quacker, func(obj *app.Quacker) {
		obj.Close()
	})

	quacker.Start()
	defer quacker.Close()
}

func getEnv(key string, defaultValue string) string {
	value, found := os.LookupEnv(key)
	if found == false {
		return defaultValue
	}
	return value
}

func getEnvOrFail(key string) string {
	value, found := os.LookupEnv(key)
	if found == false {
		panic("Need env key" + key)
	}
	return value
}
