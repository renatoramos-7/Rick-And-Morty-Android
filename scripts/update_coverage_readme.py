from pathlib import Path
import re
import xml.etree.ElementTree as ET


ROOT = Path(__file__).resolve().parent.parent
REPORT = ROOT / "RickandMorty" / "build" / "reports" / "jacoco" / "jacocoRepoReport" / "jacocoRepoReport.xml"
README_FILES = [
    ROOT / "README.md",
    ROOT / "README.pt-BR.md",
]


def get_line_coverage() -> float:
    root = ET.parse(REPORT).getroot()
    line_counter = next(counter for counter in root.findall("counter") if counter.attrib["type"] == "LINE")
    missed = int(line_counter.attrib["missed"])
    covered = int(line_counter.attrib["covered"])
    total = missed + covered
    return 0.0 if total == 0 else covered * 100.0 / total


def badge_color(coverage: float) -> str:
    if coverage >= 80:
        return "16A34A"
    if coverage >= 60:
        return "65A30D"
    if coverage >= 40:
        return "CA8A04"
    if coverage >= 20:
        return "F59E0B"
    return "DC2626"


def update_readme(readme_path: Path, coverage: float) -> None:
    coverage_text = f"{coverage:.2f}%"
    coverage_badge = (
        f"![Coverage](https://img.shields.io/badge/Coverage-"
        f"{coverage_text.replace('%', '%25')}-{badge_color(coverage)}?style=for-the-badge)"
    )

    content = readme_path.read_text(encoding="utf-8")
    content = re.sub(
        r"!\[Coverage\]\(https://img\.shields\.io/badge/Coverage-[^)]+\)",
        coverage_badge,
        content,
        count=1,
    )
    content = re.sub(
        r"(- (Current aggregated line coverage|Cobertura agregada atual de linhas): )`\d+\.\d+%`",
        rf"\1`{coverage_text}`",
        content,
        count=1,
    )

    readme_path.write_text(content, encoding="utf-8")


def main() -> None:
    coverage = get_line_coverage()
    for readme_file in README_FILES:
        update_readme(readme_file, coverage)


if __name__ == "__main__":
    main()
