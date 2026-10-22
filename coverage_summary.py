import xml.etree.ElementTree as ET
import os

path = "target/site/jacoco/jacoco.xml"
if os.path.exists(path):
    root = ET.parse(path).getroot()
    print("\n" + "="*35 + "\n   JACOCO COVERAGE SUMMARY\n" + "="*35)
    for c in root.findall("counter"):
        m, cov, miss = c.get("type"), int(c.get("covered")), int(c.get("missed"))
        tot = cov + miss
        pct = (cov / tot * 100) if tot > 0 else 0
        print(f"{m:<15}: {pct:>6.2f}% ({cov}/{tot})")
    print("="*35 + "\n")